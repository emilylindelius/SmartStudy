#!/bin/bash

# Build a macOS DMG from the existing app bundle.
# Run this on a Mac from the dist directory.

set -e

APP_BUNDLE="mac-app/SmartStudy.app"
OUTPUT_DIR="mac"
OUTPUT_DMG="${OUTPUT_DIR}/SmartStudy.dmg"

mkdir -p "${OUTPUT_DIR}"
rm -f "${OUTPUT_DMG}"

if [ ! -d "${APP_BUNDLE}" ]; then
  echo "ERROR: App bundle not found: ${APP_BUNDLE}"
  echo "Make sure you are in the dist directory and that ${APP_BUNDLE} exists."
  exit 1
fi

hdiutil create \
  -volname "SmartStudy" \
  -srcfolder "${APP_BUNDLE}" \
  -ov \
  -format UDZO \
  "${OUTPUT_DMG}"

echo "Created ${OUTPUT_DMG}"
