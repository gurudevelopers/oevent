# Fix SnapshotStateObserver Crash in LoginScreen

The application is crashing with `androidx.compose.runtime.snapshots.SnapshotStateObserver.observeReads` during the measurement pass. This typically occurs when `derivedStateOf` is used for simple state derivations that are then read during measurement or layout, especially when multiple `derivedStateOf` instances are nested or depend on each other.

In `LoginScreen.kt`, several `derivedStateOf` blocks are used for simple validation logic. These blocks are read by `OutlinedTextField` for `isError`, `supportingText`, and `trailingIcon`, which are all part of the measurement process.

## Proposed Changes

### [app/shared]

#### [MODIFY] [LoginScreen.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/login/LoginScreen.kt)

- Replace `derivedStateOf` with `remember(key) { ... }` for simple validation booleans (`isPhoneValid`, `isEmailValid`, `isPhoneError`, `isEmailError`, `isButtonEnabled`).
- Move the `emailRegex` definition into a `remember` block to avoid creating a new `Regex` object on every recomposition.
- Ensure all validation logic is calculated during composition rather than as a derived state, reducing the overhead and complexity for the `SnapshotStateObserver` during measurement.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to the Login screen.
- Type in the Mobile Number and Email fields to trigger validation.
- Verify that the error messages appear/disappear correctly without crashing.
- Verify that the "Send OTP" button enables/disables correctly.
