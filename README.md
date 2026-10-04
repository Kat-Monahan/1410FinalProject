# Avalanche Data Explorer

A program for exploring local avalanche records. It loads events from a CSV
file and lets you filter, sort, and summarize them.

Final project for CS 1410 (Object-Oriented Programming), Salt Lake Community College.

## Features
- Filter by date (e.g. "Jan 01, 2024"), place, or trigger type
- Sort by date, depth, or number of fatalities
- Summary statistics for the current results: total avalanches, average depth, total fatalities
- Save the filtered table to a file, and reset to the full data set

## Built with
Java and Swing. Each avalanche record has nine fields: date, region, place, trigger,
depth, width, vertical drop, elevation, and fatalities.

## How to run
1. Open the project in Eclipse or another Java IDE.
2. Run `AvalancheGUI.java` (it contains `main`).
3. Keep the data file in the `resources` folder so the program can find it.

## Team
- **Kathleen Monahan** – project idea; CSV loading (`AvalancheData`), date filtering
  (`DateRowFilter`), and the data table with sorting, filtering, statistics, and export (`JTablePanel`)
- **Latifah Wanyana** – avalanche data class and statistics (`Avalanche`, `AvalancheStats`)
- **Jasey Christensen** – main window and interface layout (`AvalancheGUI`)
