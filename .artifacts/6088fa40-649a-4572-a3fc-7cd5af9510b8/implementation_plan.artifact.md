# Welcome Screen Implementation Plan

Implement the first three screens of the Attendee Onboarding flow as defined in the wireframe.

## User Review Required

> [!NOTE]
> The implementation will use a simple state-based navigation within `App.kt` for this initial phase.
> Placeholders will be used for images and the specific "Event Token" logo.

## Proposed Changes

### UI Components

#### [NEW] [OnboardingScreens.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/OnboardingScreens.kt)
Create a new file containing the UI for the first three steps:
1. **Welcome/Role Selection**: Selection between Attendee and Merchant.
2. **Login/Signup**: Mobile number entry for OTP.
3. **OTP Verification**: 6-digit verification code entry.

#### [MODIFY] [App.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/App.kt)
Update the main `App` entry point to host the onboarding flow and handle navigation between the three screens.

## Verification Plan

### Automated Tests
- Run `gradlew :app:shared:jvmTest` (if unit tests are added later).

### Manual Verification
- Deploy to an Android emulator/device and verify:
  - Tapping "I'm an Attendee" navigates to the Login screen.
  - Tapping "Send OTP" navigates to the OTP Verification screen.
  - The UI matches the wireframe structure (Role Selection -> Login -> OTP).
