#!/bin/bash

# ==============================================
# RMI Registry Startup Script
# Starts the RMI registry on port 1099
# ==============================================

clear
echo "╔══════════════════════════════════════════════════════╗"
echo "║              RMI REGISTRY STARTUP                   ║"
echo "╚══════════════════════════════════════════════════════╝"
echo ""

# Configuration
RMI_PORT=1099
BIN_DIR="bin"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Check if RMI registry is already running
echo -e "${BLUE}🔍 Checking if RMI registry is already running...${NC}"
if lsof -Pi :$RMI_PORT -sTCP:LISTEN -t >/dev/null ; then
    echo -e "${YELLOW}⚠️  RMI registry is already running on port $RMI_PORT${NC}"
    echo -e "${YELLOW}   Do you want to stop it and start fresh? (y/n)${NC}"
    read -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        echo -e "${BLUE}🛑 Stopping existing RMI registry...${NC}"
        kill $(lsof -ti:$RMI_PORT) 2>/dev/null
        sleep 2
    else
        echo -e "${GREEN}✅ Using existing RMI registry${NC}"
        exit 0
    fi
fi

# Start RMI registry
echo -e "${BLUE}🚀 Starting RMI registry on port $RMI_PORT...${NC}"
echo -e "${YELLOW}📝 Note: Keep this terminal window open${NC}"
echo -e "${YELLOW}      Press Ctrl+C to stop the registry${NC}"
echo ""

# Set classpath and start registry
cd "$(dirname "$0")/.."
export CLASSPATH="$BIN_DIR"

echo -e "${GREEN}✅ RMI Registry starting...${NC}"
echo -e "${BLUE}🔧 Configuration:${NC}"
echo "   Port: $RMI_PORT"
echo "   Classpath: $CLASSPATH"
echo "   Directory: $(pwd)"
echo ""

# Start the registry in foreground
exec rmiregistry -J-Djava.class.path="$BIN_DIR" $RMI_PORT