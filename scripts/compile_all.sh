#!/bin/bash

# ==============================================
# RMI Calculator Compilation Script
# Compiles all Java source files for the project
# ==============================================

clear
echo "╔══════════════════════════════════════════════════════╗"
echo "║         RMI CALCULATOR COMPILATION SCRIPT           ║"
echo "╚══════════════════════════════════════════════════════╝"
echo ""

# Configuration
SRC_DIR="src"
BIN_DIR="bin"
JAVA_VERSION="1.8"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Check if Java is installed
echo -e "${BLUE}🔍 Checking Java installation...${NC}"
if ! command -v javac &> /dev/null; then
    echo -e "${RED}❌ Java Compiler (javac) not found!${NC}"
    echo "Please install Java Development Kit (JDK) version 8 or higher."
    exit 1
fi

JAVA_VERSION=$(javac -version 2>&1 | awk '{print $2}')
echo -e "${GREEN}✅ Java Compiler version: $JAVA_VERSION${NC}"

# Create bin directory if it doesn't exist
echo -e "\n${BLUE}📁 Creating directory structure...${NC}"
if [ ! -d "$BIN_DIR" ]; then
    mkdir -p "$BIN_DIR/remote" "$BIN_DIR/server" "$BIN_DIR/client" "$BIN_DIR/gui"
    echo -e "${GREEN}✅ Created directory: $BIN_DIR${NC}"
else
    echo -e "${YELLOW}⚠️  Directory $BIN_DIR already exists${NC}"
    read -p "Clear existing compiled classes? (y/n): " -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        rm -rf "$BIN_DIR"/*
        echo -e "${GREEN}✅ Cleared $BIN_DIR${NC}"
    fi
fi

# Compile Remote Interfaces
echo -e "\n${BLUE}🔧 Step 1: Compiling Remote Interfaces...${NC}"
if javac -d "$BIN_DIR" -sourcepath "$SRC_DIR" "$SRC_DIR/remote/CalculatorInterface.java" "$SRC_DIR/remote/CalculatorImpl.java"; then
    echo -e "${GREEN}✅ Remote interfaces compiled successfully${NC}"
else
    echo -e "${RED}❌ Failed to compile remote interfaces${NC}"
    exit 1
fi

# Compile Server
echo -e "\n${BLUE}🔧 Step 2: Compiling Server...${NC}"
if javac -d "$BIN_DIR" -cp "$BIN_DIR" "$SRC_DIR/server/CalculatorServer.java"; then
    echo -e "${GREEN}✅ Server compiled successfully${NC}"
else
    echo -e "${RED}❌ Failed to compile server${NC}"
    exit 1
fi

# Compile GUI
echo -e "\n${BLUE}🔧 Step 3: Compiling GUI...${NC}"
if javac -d "$BIN_DIR" -cp "$BIN_DIR" "$SRC_DIR/gui/CalculatorGUI.java"; then
    echo -e "${GREEN}✅ GUI compiled successfully${NC}"
else
    echo -e "${RED}❌ Failed to compile GUI${NC}"
    exit 1
fi

# Compile Client
echo -e "\n${BLUE}🔧 Step 4: Compiling Client...${NC}"
if javac -d "$BIN_DIR" -cp "$BIN_DIR" "$SRC_DIR/client/CalculatorClient.java"; then
    echo -e "${GREEN}✅ Client compiled successfully${NC}"
else
    echo -e "${RED}❌ Failed to compile client${NC}"
    exit 1
fi

# Generate Stubs (for older Java versions)
echo -e "\n${BLUE}🔧 Step 5: Generating RMI Stubs...${NC}"
if command -v rmic &> /dev/null; then
    cd "$BIN_DIR"
    if rmic remote.CalculatorImpl; then
        echo -e "${GREEN}✅ RMI stubs generated successfully${NC}"
    else
        echo -e "${YELLOW}⚠️  Could not generate RMI stubs (not required for Java 1.8+)${NC}"
    fi
    cd ..
else
    echo -e "${YELLOW}⚠️  rmic not found (not required for Java 1.8+)${NC}"
fi

# Create client policy file if it doesn't exist
echo -e "\n${BLUE}🔧 Step 6: Setting up security policy...${NC}"
if [ ! -f "client.policy" ]; then
    cat > client.policy << EOF
grant {
    permission java.security.AllPermission;
};
EOF
    echo -e "${GREEN}✅ Created client.policy file${NC}"
else
    echo -e "${YELLOW}⚠️  client.policy already exists${NC}"
fi

# Summary
echo -e "\n${GREEN}══════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}✅ COMPILATION COMPLETED SUCCESSFULLY!${NC}"
echo -e "${GREEN}══════════════════════════════════════════════════════${NC}"
echo ""
echo -e "${BLUE}📦 Compiled classes are in: $BIN_DIR/${NC}"
echo ""
echo -e "${YELLOW}🚀 To run the application:${NC}"
echo -e "${BLUE}   1. Start RMI Registry:   ${NC}./scripts/start_registry.sh"
echo -e "${BLUE}   2. Start Calculator Server:${NC}./scripts/start_server.sh"
echo -e "${BLUE}   3. Start Calculator Client:${NC}./scripts/start_client.sh"
echo ""
echo -e "${YELLOW}🔧 Quick start (all in one):${NC}"
echo -e "${BLUE}   ./scripts/start_registry.sh &${NC}"
echo -e "${BLUE}   sleep 2${NC}"
echo -e "${BLUE}   ./scripts/start_server.sh &${NC}"
echo -e "${BLUE}   sleep 2${NC}"
echo -e "${BLUE}   ./scripts/start_client.sh${NC}"
echo ""

# Make scripts executable
chmod +x scripts/*.sh
echo -e "${GREEN}✅ Made all scripts executable${NC}"