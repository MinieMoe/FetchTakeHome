# Fetch Take home assignment for Android, Mobile Engineer Apprenticeship

[Assignment](https://fetch-hiring.s3.amazonaws.com/mobile.html)

# Project Overview

This Android application fetches data from a provided API, processes it, and displays the information in a `RecyclerView` with headers and items. The data is grouped by `listId`, sorted, and displayed with headers indicating each group.

## Components

### 1. **MainActivity.kt**

- **Purpose**: Acts as the entry point of the app. It initializes UI components and handles the display of data.
- **Key Responsibilities**:
  - Implements the `FetchDataListener` interface to receive data from `ListFetcher`.
  - Initializes the `RecyclerView` and attaches the `ListAdapter`.
  - Processes fetched data and updates the adapter to refresh the UI.

### 2. **ListFetcher.kt**

- **Purpose**: Handles data fetching from the API using the Volley library.
- **Key Responsibilities**:
  - Sends an HTTP GET request to the provided API endpoint.
  - Parses the JSON response into `ListItem` objects.
  - Filters out items with null or blank names.
  - Groups items by `listId` into a map.
  - Notifies `MainActivity` via the `FetchDataListener` interface upon successful data retrieval or error.

### 3. **ListItem.kt**

- **Purpose**: Defines the data model for each item fetched from the API.
- **Structure**:
  - Fields: `id: Int`, `listId: Int`, `name: String`.

### 4. **ListAdapter.kt**

- **Purpose**: Serves as the adapter for the `RecyclerView`, managing how data is bound to view holders and displayed.
- **Key Responsibilities**:
  - Manages a list of `ListDisplayItem` objects, which can be either headers or items.
  - Provides `HeaderViewHolder` and `ItemViewHolder` for different view types.
  - Binds data to the appropriate views based on the item type.
  - Updates the data set and notifies the `RecyclerView` of data changes.

### 5. **VolleySingleton.kt**

- **Purpose**: Manages a single instance of the Volley `RequestQueue` to optimize network operations.
- **Key Responsibilities**:
  - Implements the singleton pattern to ensure only one instance of `RequestQueue` exists.
  - Provides a method to add requests to the queue from anywhere in the app.

### 6. **FetchDataListener** interface

- **Purpose**: An interface that defines callbacks for data fetching operations.
- **Methods**:
  - `onDataFetched(itemsMap: MutableMap<Int, MutableList<ListItem>>)` – Called when data fetching is successful.
  - `onError(error: VolleyError)` – Called when an error occurs during data fetching.
- Interface defined in `ListFetcher.lt`, implemented in `MainActivity.kt`

### 7. **ListDisplayItem**

- **Purpose**: Represents the different types of items displayed in the `RecyclerView`.
- **Structure**:
  - Sealed class `ListDisplayItem` with two data classes:
    - `Header(val listId: Int)` – Represents a header item.
    - `Item(val listItem: ListItem)` – Represents a regular list item.
  - Defined in `ListAdapter.kt`

### 8. **Layout Files**
- **`activity_main.xml`**: Contains `RecyclerView` for main layout.
- **`list_id_header.xml`**: Defines the layout for header views in the `RecyclerView`.
  - Contains a `TextView` to display the `listId`.
- **`list_item.xml`**: Defines the layout for regular item views.
  - Contains `TextView`s for `id`, `listId`, and `name`.

## Data Flow Summary

1. **Data Fetching Initiation**:
   - `MainActivity` creates an instance of `ListFetcher` and initiates data fetching by calling `fetchItems()`, passing itself as the listener.

2. **Data Retrieval and Processing**:
   - `ListFetcher` sends a network request to the API.
   - Parses the JSON response into `ListItem` objects.
   - Filters and groups the data by `listId`.

3. **Callback to MainActivity**:
   - Upon successful data retrieval, `ListFetcher` invokes `onDataFetched` on the listener (`MainActivity`), providing the processed data.

4. **UI Update**:
   - `MainActivity` processes the received data and updates the `ListAdapter`.
   - The `ListAdapter` refreshes the `RecyclerView`, displaying grouped and sorted items with headers.

5. **Error Handling**:
   - If an error occurs during data fetching, `ListFetcher` invokes `onError`, allowing `MainActivity` to handle the error appropriately (e.g., displaying a toast message).

## Key Concepts

- **Separation of Concerns**: Each class has a single responsibility, making the code modular and maintainable.
- **Asynchronous Operations**: Network requests are handled asynchronously to keep the UI responsive.
- **Interfaces and Callbacks**: The `FetchDataListener` interface allows `ListFetcher` to communicate with `MainActivity` without tight coupling.
- **RecyclerView Optimization**: Using multiple view types and view holders to efficiently display a complex list with headers.

## How to Use the Project

1. **Setup**:
   - Ensure you have the necessary dependencies for Volley and RecyclerView in your `build.gradle` file.

2. **Running the App**:
   - Launch the app from Android Studio.
   - The app will automatically fetch data from the API and display it in the grouped list format.

3. **Customization**:
   - Modify `ListFetcher.kt` if you need to fetch data from a different API endpoint.
   - Update the layouts (`header_layout.xml`, `list_item.xml`) to change the UI appearance.

4. **Extensibility**:
   - Implement additional features by extending the existing classes or adding new ones.
   - Follow the existing design patterns for consistency.

## Dependencies

- **Volley**: For network operations.
- **RecyclerView**: For displaying lists efficiently.
- **Kotlin**: The programming language used for all source files.


