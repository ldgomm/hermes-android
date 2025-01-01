# Hermes - Client App for Android

Hermes is a native Android application designed for clients to explore, search, and purchase products from stores. It leverages Jetpack Compose for a modern, intuitive interface and integrates advanced backend technologies to provide a seamless shopping experience.

## Features

### Product Search
- Perform natural language searches to find products easily.
- Personalized recommendations based on user preferences.

### Shopping Experience
- Explore detailed product information, including availability and pricing.
- Add products to the cart and make purchases efficiently.

### Real-Time Communication
- Integrated with Firebase Firestore for instant messaging with stores.
- Chat in real time to ask questions or negotiate details.

### Security
- Secure authentication methods for safe shopping.
- Data encryption ensures user information protection.

### Scalability and Responsiveness
- Implements Clean Architecture and MVVM patterns.
- Ensures smooth performance and scalability.

## Technologies Used

- **Frontend:** Jetpack Compose for declarative UI design.
- **Backend:** Ktor server for managing operations and secure communications.
- **Database:** MongoDB for scalable and reliable data storage.
- **Messaging:** Firebase Firestore for real-time chat functionality.

## Getting Started

### Prerequisites
- Android Studio Bumblebee or later.
- Kotlin 1.6 or later.
- Firebase account for integration.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ldgomm/hermes-android.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files to install dependencies.

### Configuration
1. Set up Firebase:
   - Download your `google-services.json` file from the Firebase Console.
   - Add it to the `app` directory of the project.
2. Configure API Keys in the `BuildConfig` file or a secure location.

### Running the App
1. Build and run the app on your emulator or device:
   ```bash
   Shift + F10
   ```

## Contributing

We welcome contributions! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes.
   ```bash
   git commit -m "Description of your changes"
   ```
4. Push to your fork.
   ```bash
   git push origin feature-name
   ```
5. Submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please reach out to:
- **Email:** support@hermesapp.com
- **GitHub Issues:** [Issue Tracker](https://github.com/ldgomm/hermes-android/issues)
