#!/bin/bash

# ==============================================
# Calculator Server Startup Script
# Starts the RMI Calculator Server
# ==============================================

clear
echo "╔══════════════════════════════════════════════════════╗"
echo "║           CALCULATOR SERVER STARTUP                 ║"
echo "╚══════════════════════════════════════════════════════╝"
echo ""

# Configuration
SERVER_CLASS="server.CalculatorServer"
BIN_DIR="bin"
RMI_PORT=1099
RMI_HOST="localhost"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Check if RMI registry is running
echo -e "${BLUE}🔍 Checking RMI registry...${NC}"
if ! lsof -Pi :$RMI_PORT -sTCP:LISTEN -t >/dev/null ; then
    echo -e "${RED}❌ RMI registry is not running on port $RMI_PORT${NC}"
    echo -e "${YELLOW}   Please start it first: ./scripts/start_registry.sh${NC}"
    exit 1
fi
echo -e "${GREEN}✅ RMI registry is running on port $RMI_PORT${NC}"

# Check if server is already running
echo -e "\n${BLUE}🔍 Checking if server is already running...${NC}"
if pgrep -f "java.*CalculatorServer" > /dev/null; then
    echo -e "${YELLOW}⚠️  Calculator server is already running${NC}"
    echo -e "${YELLOW}   Do you want to stop it and start fresh? (y/n)${NC}"
    read -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo -e "${BLUE}🛑 Stopping existing server...${NC}"
        pkill -f "java.*CalculatorServer"
        sleep 3
    else
        echo -e "${GREEN}✅ Using existing server${NC}"
        exit 0
    fi
fi

# Start the server
echo -e "\n${BLUE}🚀 Starting Calculator Server...${NC}"
cd "$(dirname "$0")/.."

# Set Java options
JAVA_OPTS="-Djava.rmi.server.codebase=file://$(pwd)/$BIN_DIR/"
JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.hostname=$RMI_HOST"
JAVA_OPTS="$JAVA_OPTS -Djava.security.policy=client.policy"

echo -e "${GREEN}✅ Server starting with configuration:${NC}"
echo "   Main Class: $SERVER_CLASS"
echo "   RMI Host: $RMI_HOST"
echo "   RMI Port: $RMI_PORT"
echo "   Codebase: file://$(pwd)/$BIN_DIR/"
echo ""

# Start server
java $JAVA_OPTS -cp "$BIN_DIR" $SERVER_CLASS