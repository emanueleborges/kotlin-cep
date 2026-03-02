# Aplicativo CEP — README

**Projeto**: Aplicativo Android para consultar CEP usando a API Brasil API (https://brasilapi.com.br/api/cep/v2/{CEP}).

## Requisitos
- Android SDK / Android Studio (recomendado) ou apenas Java + Gradle wrapper
- Gradle wrapper incluído no repositório raiz (`gradlew`)
- Biblioteca HTTP: `com.squareup.okhttp3:okhttp:5.3.2` (já adicionada em `build.gradle.kts`)

## Arquivos principais
- `app/src/main/res/layout/activity_main.xml`
- `app/src/main/java/com/example/myapplication/MainActivity.kt`
- `app/src/main/AndroidManifest.xml`
- APK gerado: `app/build/outputs/apk/debug/app-debug.apk`

## Como clonar o repositório
```bash
git clone https://github.com/emanueleborges/kotlin-cep.git
cd kotlin-cep/app
```

> Observação: o `gradlew` está no diretório raiz do projeto (acima de `app/`).

## Primeira configuração (permissões)
- Garanta que o wrapper esteja executável (Linux/macOS):

```bash
chmod +x ../gradlew   # execute no diretório app/ ou no root do projeto conforme seu fluxo
```

No Windows (cmd/PowerShell) não é necessário `chmod`; use `gradlew.bat` ou `./gradlew.bat`.

## Comandos multiplataforma (Linux / macOS / Windows)

Construir APK (a partir do diretório raiz do projeto — recomendado):

Linux/macOS (bash/zsh):
```bash
cd /path/to/kotlin-unicamp
./gradlew :app:clean :app:assembleDebug
```

Windows (cmd):
```cmd
cd \path\to\kotlin-unicamp
gradlew.bat :app:clean :app:assembleDebug
```

Windows (PowerShell):
```powershell
Set-Location C:\path\to\kotlin-unicamp
.\gradlew.bat :app:clean :app:assembleDebug
```

Se você estiver dentro de `app/`, use o wrapper relativo:

Linux/macOS:
```bash
../gradlew :app:clean :app:assembleDebug
```

Windows (cmd / PowerShell):
```cmd
..\gradlew.bat :app:clean :app:assembleDebug
```

### Iniciar e usar o emulador

Listar AVDs:

Linux/macOS/Windows (se `emulator` estiver no PATH):
```bash
emulator -list-avds
```

Iniciar AVD (Linux/macOS):
```bash
emulator -avd <AVD_NAME> &
```

Iniciar AVD (Windows - cmd/PowerShell):
```cmd
emulator -avd <AVD_NAME>
```

Verificar dispositivos conectados:
```bash
adb devices
# exemplo: emulator-5554   device
```

Instalar e iniciar o app no emulador:

Linux/macOS:
```bash
./gradlew :app:installDebug
adb shell am start -n com.example.myapplication/.MainActivity
```

Windows (cmd/PowerShell):
```cmd
gradlew.bat :app:installDebug
adb shell am start -n com.example.myapplication/.MainActivity
```

## Copiar APK gerado para área de trabalho (exemplos)

macOS / Linux:
```bash
cp app/build/outputs/apk/debug/app-debug.apk ~/Desktop/emanuel-borges.apk
```

Windows (cmd):
```cmd
copy app\build\outputs\apk\debug\app-debug.apk %USERPROFILE%\Desktop\emanuel-borges.apk
```

Windows (PowerShell):
```powershell
Copy-Item -Path "app\build\outputs\apk\debug\app-debug.apk" -Destination "$env:USERPROFILE\Desktop\emanuel-borges.apk"
```

## Como testar a funcionalidade (consulta CEP)
1. Abra o app no emulador.
2. No campo CEP digite um CEP válido (8 dígitos) sem traços, ex.: `13083852`.
3. Toque em `Buscar`.
4. O resultado JSON retornado pela API será exibido na área de resultado.

## Comportamento e validações implementadas
- Validação: campo CEP vazio ou com comprimento diferente de 8 impede requisição e exibe mensagem.
- Requisição HTTP feita com `OkHttp` usando `enqueue()` (assíncrono). Resposta exibida na UI via `runOnUiThread`.
- Permissão `INTERNET` adicionada em `AndroidManifest.xml`.

## Testes adicionais
- Para testar falhas, desligue a rede do host/emulador e verifique mensagem de erro exibida.
- Para testes unitários do código HTTP, o projeto pode ser ampliado com mocks de `OkHttp`.

## Observações finais
- Existem avisos de depreciação do AGP/Kotlin no build (opções em `gradle.properties`). Eles não impedem a execução; se desejar, eu posso migrar para o Kotlin "built-in" e remover as flags depreciadas.
- Se preferir, posso criar um branch `deliverable` com o `README.md` já commitado e um release contendo o APK.

## Entrega / Contato
- APK final copiado para `~/Desktop/emanuel-borges.apk` (local do host onde gerei o APK).
- Quer que eu faça o commit e push do `README.md` para seu repositório remoto agora? (responda `sim`/`não`).
