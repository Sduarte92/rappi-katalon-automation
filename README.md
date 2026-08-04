# Mobile Automation — Katalon Studio

> **Mobile test automation project developed at Rappi** using Katalon Studio + Appium for Android. Automates critical user flows in the Rappi Consumer app: login, order tracking, and OnTop feature validation.

---

## Test Cases

| Test Case | Type | Description |
|---|---|---|
| `PORD-T230` | Automated (Android) | Validates the OnTop section appears when navigating to an active order detail. Includes smart scroll logic to reach the final CTA button. |
| `PORD-T1317` | Automated (Android) | Login + order flow validation on the Consumer app. |
| `TC_TextBox_HappyPath` | Automated (Web) | Happy path validation for a text input form — fills all fields and verifies successful submission. |
| `TC_TextBox_EmailVacio` | Automated (Web) | Negative test — submits form with empty email field and validates the error state. |
| `tc_solo_login` | Automated (Android) | Isolated login test using the `LoginRappi` custom keyword with email + OTP flow. |

---

## Architecture

```
rappi-katalon-automation/
├── Test Cases/               ← .tc definitions (Zephyr-linked keys: PORD-Txxx)
│   ├── PORD-T230.tc
│   ├── PORD-T1317.tc
│   └── tc_test/
│       ├── TC_TextBox_HappyPath.tc
│       └── TC_TextBox_EmailVacio.tc
│
├── Scripts/                  ← Groovy implementation of each test case
│   ├── PORD-T230/            ← OnTop section validation + smart scroll
│   ├── PORD-T1317/           ← Login + order flow
│   └── tc_test/
│       ├── TC_TextBox_HappyPath/
│       └── TC_TextBox_EmailVacio/
│
├── Keywords/
│   └── com/rappi/automation/
│       └── LoginRappi.groovy ← Reusable custom keyword: email + OTP login flow
│
├── Object Repository/
│   ├── PORD/OT/              ← UI elements: OnTop section, order card, inputs
│   └── Page_TextBox/         ← Web form elements
│
└── Profiles/
    ├── default.glbl          ← GlobalVariables: appId, ambiente, timeouts
    └── dev.glbl              ← Dev environment config
```

---

## Key Technical Details

### Custom Keyword — `LoginRappi.groovy`
Reusable keyword that encapsulates the full email + OTP login flow for the Rappi Consumer app. Called by multiple test cases via `CustomKeywords.'com.rappi.automation.LoginRappi.iniciarSesionEmail'(email, otp)`.

### Smart Scroll Logic (PORD-T230)
Uses dynamic XPath to detect the active order card regardless of its state text (handles `delivery`, `Estimated`, `Entrega` variants). Implements a configurable scroll loop (`maxScrolls` global variable) with device-height-based swipe coordinates to reach the target element reliably across different screen sizes.

### Dynamic Object Creation
Tests create `TestObject` instances at runtime using XPath with `contains()` for resilience against minor UI text changes, instead of relying on hardcoded element IDs.

### Global Variables
All environment-specific values are managed via Katalon Profiles:
- `appId` — App package identifier
- `ambiente` — Environment name for logging
- `timeoutGeneral` / `timeoutScroll` — Configurable wait times
- `maxScrolls` — Max scroll attempts before failing

---

## Stack

| Tool | Version |
|---|---|
| Katalon Studio | 10.x |
| Appium | 2.x |
| Groovy | 3.x |
| Platform | Android (Consumer App) + Web |
| Test Management | Zephyr Scale (PORD project) |

---

## Author

**Josepher Duarte** — QA Engineer / QA Automation  
Rappi Inc. · 2025–2026
