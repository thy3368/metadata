#!/bin/bash

cd ../
if [ "$1" == "version" ]; then
  mvn clean install
  mvn deploy
  git checkout -b release/"$1"
  git add .
  git commit -m "release $1"
  git tag v"$1"
  git push --set-upstream origin release/"$1"
  git push origin v"$1"
  git checkout master
fi

if [ "$1" == "snapshot" ]; then
  mvn clean install
  mvn deploy
fi