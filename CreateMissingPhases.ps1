$winBase = "desktop\LinkBridge.Windows\Services"
$andBase = "android\app\src\main\java\com\linkbridge\app"

# New Windows Directories
$winDirs = @(
    "$winBase\Telephony", "$winBase\Battery"
)

foreach ($dir in $winDirs) {
    if (!(Test-Path $dir)) { New-Item -ItemType Directory -Force -Path $dir | Out-Null }
}

# New Android Directories
$andDirs = @(
    "$andBase\telephony", "$andBase\battery", "$andBase\permissions"
)

foreach ($dir in $andDirs) {
    if (!(Test-Path $dir)) { New-Item -ItemType Directory -Force -Path $dir | Out-Null }
}

function Write-WinFile($path, $namespace, $className) {
    $content = "using System;`nusing System.Diagnostics;`n`nnamespace LinkBridge.Windows.Services.$namespace`n{`n    public class $className`n    {`n        public void Initialize()`n        {`n            Debug.WriteLine(""$className initialized."");`n        }`n    }`n}"
    Set-Content -Path $path -Value $content
}

function Write-AndFile($path, $package, $className) {
    $content = "package com.linkbridge.app.$package`n`nimport android.util.Log`n`nclass $className {`n    fun initialize() {`n        Log.d(""$className"", ""Initialized"")`n    }`n}"
    Set-Content -Path $path -Value $content
}

# Windows
Write-WinFile "$winBase\Telephony\TelephonyService.cs" "Telephony" "TelephonyService"
Write-WinFile "$winBase\Battery\BatterySyncService.cs" "Battery" "BatterySyncService"

# Android
Write-AndFile "$andBase\telephony\CallManager.kt" "telephony" "CallManager"
Write-AndFile "$andBase\telephony\SmsManager.kt" "telephony" "SmsManager"
Write-AndFile "$andBase\battery\BatterySyncManager.kt" "battery" "BatterySyncManager"
Write-AndFile "$andBase\permissions\PermissionManager.kt" "permissions" "PermissionManager"

Write-Output "Additional structural files created successfully."
