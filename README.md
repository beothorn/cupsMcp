

```json
{
  "mcpServers": {
    "print-server": {
      "command": "java",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "/absolute/path/to/print-server-1.0.0.jar"
      ],
      "env": {
        "PRINTER_IP": "localhost",
        "PRINTER_NAME": "Your printer",
        "PRINT_FOLDER": "/home/me/myPrintableFiles"
      }
    }
  }
}
```