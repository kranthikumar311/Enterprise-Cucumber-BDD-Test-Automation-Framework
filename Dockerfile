# Use Maven with JDK 17 as base image
FROM maven:3.9-eclipse-temurin-17

# Install Chrome browser
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    unzip \
    && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Download dependencies (cached layer)
RUN mvn dependency:resolve

# Set timezone to MST
RUN ln -sf /usr/share/zoneinfo/America/Denver /etc/localtime && echo "America/Denver" > /etc/timezone


# Default command — run smoke tests headless
ENTRYPOINT ["mvn", "clean", "test"]
CMD ["-Psmoke", "-Denv=qa", "-Dbrowser=chrome", "-Dheadless=true"]