# EssentialRX (Minecraft 1.8.9)

Plugin essentials-like para Spigot 1.8.8/1.8.9 com hook em Vault (Economia + Permissões).
Compatível com LuckPerms via Vault.

## Requisitos
- Java 8
- Spigot/PaperSpigot 1.8.8/1.8.9
- Vault
- (Opcional) LuckPerms
- (Opcional) Plugin de economia compatível com Vault

## Build
```bash
mvn clean package
```

O JAR final fica em:
`target/EssentialRX-1.0.0.jar`

## Instalação
1. Coloque o `EssentialRX-1.0.0.jar` na pasta `plugins/`
2. Inicie o servidor (ele vai gerar config.yml e messages.yml)
3. Configure permissões conforme necessário

## Principais comandos
- /spawn, /setspawn
- /tp, /tphere, /tpall
- /tpa, /tpaccept, /tpdeny
- /back
- /home, /sethome, /delhome
- /warp, /setwarp, /delwarp
- /heal, /feed, /fly
- /gm, /gms, /gmc, /gma
- /clearinventory, /invsee, /enderchest
- /workbench, /anvil, /trash
- /hat, /repair, /kill
- /msg, /reply, /broadcast, /seen
- /bal, /pay, /baltop
- /kick, /ban, /tempban, /unban
- /mute, /unmute
- /vanish

## Segurança
- Validações server-side em todos comandos
- Ban/mute persistente em YAML
- Vanish persistente

## Expansão
O projeto é modular para adicionar mais comandos facilmente.

