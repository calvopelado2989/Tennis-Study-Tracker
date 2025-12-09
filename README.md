# Tennis & Study Tracker

A simple Java console application to track tennis training and study hours, in order to maintain a healthy balance.

## Features

- **Log Daily Activity**: Record tennis hours, matches played, study hours, and assignments completed.
- **Persistence**: Data is automatically saved to `tennis_study_log.csv` immediately after every entry.
- **Analytics**:
    - **Weekly Study Goal**: Tracks your progress towards a 35-hour weekly study goal.
    - **Summaries**: View total hours and stats for all tracked days.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed.

### Running the Application

1.  **Compile**:
    ```bash
    javac -d bin src/TennisStudyTracker.java
    ```

2.  **Run**:
    ```bash
    java -cp bin TennisStudyTracker
    ```

## Usage

1.  **Add Day Log**: Enter the date, tennis hours, matches, study hours, and assignments.
2.  **Show Summary**: View your total stats and progress towards your weekly study goal.
3.  **Exit**: Close the application.

## Data Storage
All data is stored in `tennis_study_log.csv` in the project root directory. You can view or back up this file at any time.
