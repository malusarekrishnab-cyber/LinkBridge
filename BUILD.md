# Build Instructions

## Android Client
1. Ensure you have the Android SDK installed.
2. Set `JAVA_HOME` to a valid JDK 17 installation.
3. Open the `android` folder in Android Studio OR run the following from the command line:
   ```bash
   ./gradlew assembleDebug
   ```

## Windows Client
1. Ensure you have the .NET 8 SDK installed.
2. Open the `desktop/LinkBridge.Windows` folder in Visual Studio 2022 OR run the following from the command line:
   ```bash
   dotnet build
   ```
