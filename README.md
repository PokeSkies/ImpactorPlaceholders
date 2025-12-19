# ImpactorPlaceholders
<img height="50" src="https://camo.githubusercontent.com/a94064bebbf15dfed1fddf70437ea2ac3521ce55ac85650e35137db9de12979d/68747470733a2f2f692e696d6775722e636f6d2f6331444839564c2e706e67" alt="Requires Fabric Kotlin"/>

A Fabric server-sided Impactor placeholders extension. Adds useful Impactor related placeholders to use anywhere that MiniPlaceholders, PlaceholderAPI, or Impactor placeholders are parsed.

## Placeholders
- Any [currency] placeholder is optional. If not provided, the default primary currency will be used. Argument can be either the currency's full ID (ex: `impactor:dollars`) or just the value with impactor implied (ex: `dollars` -> `impactor:dollars`)
- Any [decimals] placeholder is optional. If not provided, two decimal places will be used
- Strip placeholders will remove any trailing zeros after the decimal point as well as the decimal point if possible (ex: `100.00` -> `100`, `100.50` -> `100.5`)

### MiniPlaceholders
| Placeholder                                              | Info                                                                                    | Return Type |
|----------------------------------------------------------|-----------------------------------------------------------------------------------------| ----------- |
| <impactor_currency_plural:[currency]>                    | Plural form of the default or provided currency                                         | string      |
| <impactor_currency_singular:[currency]>                  | Singular form of the default or provided currency                                       | string      |
| <impactor_currency_symbol:[currency]>                    | Symbol the default or provided currency                                                 | string      |
| <impactor_balance:[currency]:[decimals]>                 | Player's balance                                                                        | double      |
| <impactor_balance_strip:[currency]:[decimals]>           | Player's balance with trailing zeros removed                                            | double      |
| <impactor_balance_formatted:[currency]:[decimals]>       | Comma separated version of the player's balance                                         | double      |
| <impactor_balance_formatted_strip:[currency]:[decimals]> | Comma separated version of the player's balance with trailing zeros removed             | double      |
| <impactor_balance_short:[currency]:[decimals]>           | Short format (ex: 1K, 1.2M) version of the player's balance                             | double      |
| <impactor_balance_short_strip:[currency]:[decimals]>     | Short format (ex: 1K, 1.2M) version of the player's balance with trailing zeros removed | double      |

### PlaceholderAPI
| Placeholder                                              | Info                                                                                    | Return Type |
|----------------------------------------------------------|-----------------------------------------------------------------------------------------| ----------- |
| %impactor:currency_plural [currency]%                    | Plural form of the default or provided currency                                         | string      |
| %impactor:currency_singular [currency]%                  | Singular form of the default or provided currency                                       | string      |
| %impactor:currency_symbol [currency]%                    | Symbol the default or provided currency                                                 | string      |
| %impactor:balance [currency]:[decimals]%                 | Player's balance                                                                        | double      |
| %impactor:balance_strip [currency]:[decimals]%           | Player's balance with trailing zeros removed                                            | double      |
| %impactor:balance_formatted [currency]:[decimals]%       | Comma separated version of the player's balance                                         | double      |
| %impactor:balance_formatted_strip [currency]:[decimals]% | Comma separated version of the player's balance with trailing zeros removed             | double      |
| %impactor:balance_short [currency]:[decimals]%           | Short format (ex: 1K, 1.2M) version of the player's balance                             | double      |
| %impactor:balance_short_strip [currency]:[decimals]%     | Short format (ex: 1K, 1.2M) version of the player's balance with trailing zeros removed | double      |

### Impactor
| Placeholder                                                           | Info                                                                                    | Return Type |
|-----------------------------------------------------------------------|-----------------------------------------------------------------------------------------| ----------- |
| <impactor_placeholders:currency_plural:[currency]>                    | Plural form of the default or provided currency                                         | string      |
| <impactor_placeholders:currency_singular:[currency]>                  | Singular form of the default or provided currency                                       | string      |
| <impactor_placeholders:currency_symbol:[currency]>                    | Symbol the default or provided currency                                                 | string      |
| <impactor_placeholders:balance:[currency]:[decimals]>                 | Player's balance                                                                        | double      |
| <impactor_placeholders:balance_strip:[currency]:[decimals]>           | Player's balance with trailing zeros removed                                            | double      |
| <impactor_placeholders:balance_formatted:[currency]:[decimals]>       | Comma separated version of the player's balance                                         | double      |
| <impactor_placeholders:balance_formatted_strip:[currency]:[decimals]> | Comma separated version of the player's balance with trailing zeros removed             | double      |
| <impactor_placeholders:balance_short:[currency]:[decimals]>           | Short format (ex: 1K, 1.2M) version of the player's balance                             | double      |
| <impactor_placeholders:balance_short_strip:[currency]:[decimals]>     | Short format (ex: 1K, 1.2M) version of the player's balance with trailing zeros removed | double      |

## Support
A community support Discord has been opened up for all Skies Development related projects! Feel free to join and ask questions or leave suggestions :)

<a class="discord-widget" href="https://discord.gg/cgBww275Fg" title="Join us on Discord"><img src="https://discordapp.com/api/guilds/1158447623989116980/embed.png?style=banner2"></a>
