# Basic Angular SPA w/ Java Spring Boot API

Just a basic demo of the above technologies. This project is not likely to be useful to anyone in any way.

## Server

If you want to run this locally (I don't expect that you will), you need the following

* Java 17 JRE/JDK
* MySQL DB called AudioEncoder (empty), with a user called `audioencoder` with password `supersecretpassword123` that has access to the DB.
* ffprobe and ffmpeg installed and in your PATH

Run with `./server/gradlew bootRun`.

If you're lucky, I'll create a Dockerfile soon so none of that is necessary.

## Client

Just run `pnpm i` and `pnpm run serve` and you're good to go.

This part is still a WIP.
