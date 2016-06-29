# Statusbar Eclipse E4 Plugin

## Introduction

Statusbar is a fragment plugin which adds a status line to a pure E4 application.
Using the Eclipse 4 Model Editor, simply add to the main window a "Window Trim - Bottom" with ID "au.com.cybersearch2.cybertete.trimbar.1".
Here is an example:


![Eclipse 4 Model Editor](/images/model-editor.png)
Format: ![Eclipse 4 Model Editor](url)

 
The plugin expects to find a class in the Eclipse context which implements interface **au.com.cybersearch2.statusbar.IStatusBar**.
This interface requires two overrides:

1. '<StatusLine getStatusLine(Composite parent)>`

2. '<List<StatusLineContribution> getContributions()>`

The getStatusLine() method returns 