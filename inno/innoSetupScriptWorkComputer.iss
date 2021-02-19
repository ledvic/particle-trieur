; Script generated by the Inno Script Studio Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "ParticleTrieur"
#define MyAppVersion "2.1.0-beta"
#define MyAppPublisher "Ross Marchant"
#define MyAppURL "www.imageprocessing.com.au"
#define MyAppExeName "ParticleTrieur.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{298B3E74-D0FE-47C4-8B59-423BD4B1FC42}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputDir=C:\Users\rossm\Documents\Development\ForamTrieur\inno
OutputBaseFilename=ParticleTrieurSetup
SetupIconFile=C:\Users\rossm\Documents\Development\ForamTrieur\src\main\particletrieur\package\windows\ParticleTrieur.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\rossm\Documents\Development\ForamTrieur\out\artifacts\ParticleTrieur\bundles\ParticleTrieur\ParticleTrieur.exe"; DestDir: "{particletrieur}"; Flags: ignoreversion
Source: "C:\Users\rossm\Documents\Development\ForamTrieur\out\artifacts\ParticleTrieur\bundles\ParticleTrieur\*"; DestDir: "{particletrieur}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{particletrieur}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{particletrieur}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{particletrieur}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent
