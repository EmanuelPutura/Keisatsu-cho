#!/bin/bash

timestamp=$(date +%Y.%m.%d_%H:%M:%S)

cd ~/UBB/SE/Keisatsu-cho/reliability

mkdir ./backups/$timestamp

pg_dump cms > ./backups/$timestamp/database.sql
cp -R ../uploaded ./backups/$timestamp/uploaded
