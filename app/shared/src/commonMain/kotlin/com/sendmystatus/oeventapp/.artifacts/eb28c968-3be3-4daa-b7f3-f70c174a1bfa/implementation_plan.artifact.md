# Implementation Plan - Invitations Screen

Implement the Invitations screen as per the provided design, including its ViewModel and navigation integration.

## Proposed Changes

### [Navigation & Routes]

#### [MODIFY] [Routes.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/Routes.kt)
- Add `Invitations` to the `Route` sealed interface.

### [ViewModel]

#### [NEW] [InvitationViewModel.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/viewmodel/InvitationViewModel.kt)
- Create `InvitationViewModel` with `InvitationUiState`.
- `InvitationUiState` will include a list of invitations and a notification count.
- Handle actions for joining events, entering codes, and scanning QR codes.

### [UI Components]

#### [NEW] [InvitationScreen.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/Invitation/InvitationScreen.kt)
- Implement `InvitationScreen` with:
    - Custom Top Bar with notification badge.
    - Empty state illustration (Envelope).
    - "Join an Event" primary button.
    - "OR" divider.
    - "Have an event code?" section with "Enter Code" and "Scan QR" buttons.
    - Tip card with lightbulb icon.

### [Dashboard Integration]

#### [MODIFY] [DashBoardScreen.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/dashboard/DashBoardScreen.kt)
- Add `composable<Route.Invitations>` to the `NavHost`.
- Update `DashboardBottomNavigation` to handle navigation to the Invitations screen and show the selection state.

## Verification Plan

### Automated Tests
- N/A (UI focused task, will verify via Previews)

### Manual Verification
- Render the `InvitationScreenPreview` to ensure it matches the design.
- Verify navigation from the Dashboard's bottom bar to the Invitations screen.
