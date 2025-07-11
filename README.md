# HeheDownloader

A powerful Android video downloader app that supports downloading videos from YouTube, Facebook, Twitter, and 100+ other platforms.

## Features

- **Multi-Platform Support**: Download videos from YouTube, Facebook, Twitter, Instagram, TikTok, and many more sites
- **High Quality Downloads**: Automatically selects the best available video and audio quality
- **Simple Interface**: Clean, user-friendly Material Design 3 interface
- **Clipboard Integration**: Easy URL pasting from clipboard
- **Progress Tracking**: Real-time download progress with percentage display
- **Error Handling**: Detailed error messages for troubleshooting
- **Storage Management**: Saves videos to device Downloads folder

## Technical Stack

- **Kotlin**: Primary language for Android development
- **Jetpack Compose**: Modern UI framework
- **Chaquopy**: Python integration for yt-dlp functionality
- **Mobile-FFmpeg**: Video processing and merging
- **yt-dlp**: Powerful video downloader engine

## Requirements

- Android 8.0 (API 26) or higher
- Internet connection
- Storage permission for saving videos

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the app

## Permissions

The app requires the following permissions:
- **Internet**: For downloading videos
- **Storage**: For saving downloaded videos to device
- **Network State**: For checking connectivity

## Usage

1. Launch the app
2. Paste a video URL or type it manually
3. Tap "Get Video Info" to preview video details
4. Tap "Download Video" to start downloading
5. Monitor progress and wait for completion
6. Find downloaded videos in your Downloads folder

## Supported Platforms

- YouTube
- Facebook
- Twitter
- Instagram
- TikTok
- Vimeo
- Dailymotion
- And 100+ more sites supported by yt-dlp

## Development

This project uses:
- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 36 (Android 14)
- **Kotlin**: 2.0.21
- **Compose BOM**: 2024.09.00

## License

This project is for educational purposes. Please respect the terms of service of the platforms you download from.

## Disclaimer

This app is intended for personal use only. Users are responsible for complying with the terms of service of the platforms they download from. The developers are not responsible for any misuse of this application. 