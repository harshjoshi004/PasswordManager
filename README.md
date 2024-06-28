
# Password Manager Application Documentation

## Overview

This documentation provides a guide for building, running, and using the Password Manager application. The application is designed to securely store and manage user passwords with features for adding, viewing, editing, and deleting passwords.

## Features

### Functional Requirements

1. **Add Password**
   - Users can securely add new passwords by providing details such as account type (e.g., Gmail, Facebook, Instagram), username/email, and password.

2. **View/Edit Password**
   - Users can view and edit existing passwords, including account details like username/email and password.

3. **Show List of Passwords on Home Screen**
   - The home screen displays a list of all saved passwords, showing essential details for each entry.

4. **Delete Password**
   - Users can delete passwords.

### Technical Requirements

1. **Encryption**
   - Implements strong encryption algorithms (e.g., AES) to secure password data.

2. **Database**
   - Uses a secure database (e.g., Room) to manage encrypted passwords locally on the device.

3. **User Interface**
   - Designs a clean and intuitive user interface for managing passwords.

4. **Input Validation**
   - Implements validation to ensure that mandatory fields are not empty.

5. **Error Handling**
   - Properly handles errors and edge cases to ensure a smooth user experience.

### Bonus Features (Optional)

1. **Security Feature**
   - Prompts users to authenticate using biometric (e.g., fingerprint, face ID) or a PIN screen for added security.

2. **Password Strength Meter**
   - Provides a visual indicator of password strength to help users create strong passwords.

3. **Password Generation**
   - Includes a feature to generate strong, random passwords for new entries.

## Setup and Installation

### Prerequisites

- Android Studio
- An Android device or emulator running Android 6.0 (Marshmallow) or higher

### Steps

1. **Clone the Repository**

   ```sh
   git clone <repository-link>
   ```

2. **Open the Project in Android Studio**
   - Open Android Studio.
   - Select "Open an existing Android Studio project."
   - Navigate to the cloned repository and open it.

3. **Build the Project**
   - Click on the "Build" menu.
   - Select "Rebuild Project."

4. **Run the Application**
   - Connect an Android device or start an emulator.
   - Click on the "Run" button in Android Studio.

## Usage

### Adding a Password

1. Open the application.
2. Click on the "Add Password" button.
3. Fill in the required details (account type, username/email, password).
4. Click "Save" to securely store the password.

### Viewing and Editing a Password

1. On the home screen, select a password entry from the list.
2. View the details.
3. To edit, click on the "Edit" button, make the necessary changes, and click "Save."

### Deleting a Password

1. On the home screen, select a password entry from the list.
2. Click on the "Delete" button to remove the password.

## Code Snippets

### Encryption and Decryption

```kotlin
// Encrypt data
fun encrypt(data: String, key: SecretKey): ByteArray {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(data.toByteArray())
}

// Decrypt data
fun decrypt(encryptedData: ByteArray, key: SecretKey): String {
    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, key)
    val decryptedData = cipher.doFinal(encryptedData)
    return String(decryptedData)
}
```

### Room Database

```kotlin
@Entity(tableName = "passwords")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val accountType: String,
    val username: String,
    val encryptedPassword: ByteArray
)

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(password: Password)

    @Query("SELECT * FROM passwords")
    fun getAllPasswords(): LiveData<List<Password>>

    @Delete
    suspend fun delete(password: Password)
}

@Database(entities = [Password::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}
```

### Password Validation

```kotlin
fun passwordIsCorrect(password: String, passwordValue: MutableState<String>): Boolean {
    var error = ""

    if (password.length < 8) {
        error = "Password must be at least 8 characters long"
    } else if (!password.any { it.isUpperCase() }) {
        error = "Password must contain at least one uppercase letter"
    } else if (!password.any { it.isLowerCase() }) {
        error = "Password must contain at least one lowercase letter"
    } else if (!password.any { it.isDigit() }) {
        error = "Password must contain at least one digit"
    } else if (!password.any { "!@#$%^&*()-_=+<>?/{}~|".contains(it) }) {
        error = "Password must contain at least one special character"
    }

    passwordValue.value = error
    return error.isEmpty()
}
```

## Conclusion

The Password Manager application is a secure and user-friendly tool for managing passwords. By following this documentation, you should be able to build, run, and use the application effectively. The implementation includes robust encryption, a secure database, and a clean user interface, ensuring both functionality and security.
