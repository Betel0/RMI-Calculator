#!/bin/bash

# ==============================================
# Calculator Client Startup Script
# Starts the RMI Calculator Client
# ==============================================

clear
echo "╔══════════════════════════════════════════════════════╗"
echo "║           CALCULATOR CLIENT STARTUP                 ║"
echo "╚══════════════════════════════════════════════════════╝"
echo ""

# Configuration
CLIENT_CLASS="client.CalculatorClient"
BIN_DIR="bin"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

# Check if compiled classes exist
echo -e "${BLUE}🔍 Checking for compiled classes...${NC}"
if [ ! -f "$BIN_DIR/client/CalculatorClient.class" ]; then
    echo -e "${RED}❌ Compiled classes not found${NC}"
    echo -e "${YELLOW}   Please compile first: ./scripts/compile_all.sh${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Compiled classes found${NC}"

# Check for client policy file
echo -e "\n${BLUE}🔍 Checking security policy...${NC}"
if [ ! -f "client.policy" ]; then
    echo -e "${YELLOW}⚠️  client.policy not found, creating default...${NC}"
    cat > client.policy << EOF
grant {
    permission java.security.AllPermission;
};
EOF
    echo -e "${GREEN}✅ Created client.policy${NC}"
fi

# Start the client
echo -e "\n${BLUE}🚀 Starting Calculator Client...${NC}"
cd "$(dirname "$0")/.."

# Set Java options
JAVA_OPTS="-Djava.security.policy=client.policy"

echo -e "${GREEN}✅ Launching Modern RMI Calculator GUI${NC}"
echo ""

# Start client
java $JAVA_OPTS -cp "$BIN_DIR" $CLIENT_CLASS