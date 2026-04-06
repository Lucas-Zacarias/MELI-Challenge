# Challenge MELI - Space Flight News App

An Android application developed as a technical challenge that fetches and displays the latest news about space flights using the **SpaceFlight News API**.

## 🚀 Features
- **Latest News**: Real-time fetching of space-related articles.
- **Search**: Dynamic search functionality to find specific articles by title or content.
- **Detailed View**: Comprehensive view of each article including authors, publication date, and summary.
- **Responsive Design**: Fully compatible with screen rotations and theme changes without losing state or re-triggering API calls.

## 🏗️ Architecture & Clean Code
The project follows **Clean Architecture** principles, separating the code into three distinct layers:
- **Domain**: Contains the business logic, models, and repository interfaces.
- **Data**: Implements the repository, API calls (Remote), and data mapping (Mappers).
- **UI (Presentation)**: Jetpack Compose screens and ViewModels following the **MVVM** (Model-View-ViewModel) pattern.

## 🛠️ Tech Stack & Libraries
- **Kotlin**: Primary language for development.
- **Jetpack Compose**: Modern toolkit for building native UI.
- **Ktor**: Used as the networking client for its multiplatform capabilities and lightweight nature.
- **Koin**: Dependency Injection framework, chosen for its simplicity and "Kotlin-first" approach.
- **Coil (v3)**: Image loading library used to fetch and cache images from URLs efficiently.
- **Timber**: Logging utility to manage debug logs safely and effectively.
- **Kotlinx Serialization**: For parsing JSON responses from the API.

## 🔄 State Management & Optimization
To provide a robust user experience, the app uses **StateFlow** within the ViewModels. 

A key optimization implemented is the use of the `.stateIn()` operator:
```kotlin
val uiState: StateFlow<SearchUiState> = _searchQuery
    .flatMapLatest { ... }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchUiState.Loading
    )
```
This approach ensures that:
1.  **Duplicate API calls are avoided**: When the device rotates or the theme changes (Configuration Changes), the `StateFlow` retains its current value.
2.  **Resource Efficiency**: The `WhileSubscribed(5000)` strategy keeps the flow active for 5 seconds after the UI stops observing, allowing for seamless transitions without unnecessary network requests.

## 📡 API Implementation
The app consumes the **SpaceFlight News API (v4)**.
- **Endpoints**: Used for fetching the latest articles, searching by query, and retrieving specific article details.
- **Mapping**: Data Transfer Objects (DTOs) are converted to Domain Models to decouple the UI from the API structure.

## 🧪 Testing
The project includes:
- **Unit Tests**: Testing Mappers, Repositories, and ViewModels using **MockK** and **Turbine**.
- **UI Tests**: Instrumented tests using the **Compose Test Rule** to verify screen states (Success, Loading, Error) and user interactions.