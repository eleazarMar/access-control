#!/bin/sh

set -eu

echo "Checking DB connection ..."

i=0
until [ $i -ge 36 ]
do
#  mysql -h db -u root -p=rootpassword && break
  nc -z db 3306 && break

  i=$(( i + 1 ))

  echo "$i: Waiting for DB 5 seconds ..."
  sleep 5
done

if [ $i -eq 36 ]
then
  echo "DB connection refused, terminating ..."
  exit 1
fi

echo "DB is up ..."

./bin/access-control
