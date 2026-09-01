# Fix NavController IllegalStateException

This plan addresses the `java.lang.IllegalStateException: You must call setGraph() before calling getGraph()` error by ensuring that the `NavController` used for navigation is correctly attached to a `NavHost`.

## User Review Required

> [!IMPORTANT]
> The `DashBoardScreen` composable now requires a `NavController` parameter. If you were using this screen in other places without a `NavController`, you will need to provide one (usually the one from your `NavHost`).

## Proposed Changes

### UI Components

#### [MODIFY] [DashboardScreen.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/ui/dashboard/DashboardScreen.kt)
- Removed local `rememberNavController()` in `DashboardBottomNavigation`.
- Updated `DashBoardScreen` and `DashboardBottomNavigation` to accept `NavController` as a parameter.
- Updated `DashBoardPreview` to pass a `NavController`.

#### [MODIFY] [App.kt](file:///Users/dibyajyotidalai/development/Mobile/OEventApp/app/shared/src/commonMain/kotlin/com/sendmystatus/oeventapp/App.kt)
- Passed the main `navController` to `DashBoardScreen` within the `NavHost` composable.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to the Dashboard.
- Click on the "Events" tab in the bottom navigation bar.
- Verify that it navigates to the Event Template screen without crashing.
