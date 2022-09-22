#! /usr/bin/env bash
curl http://hospital.loc/reglog/registration/ -X POST -H "Content-Type:application/json" -d '{"name":"mario","surname":"rossi","username":"mario","pass":"pass","type":"m"}'
for((i=0;i<30;i++)); do
	curl http://hospital.loc/reglog/login/access/mario/pass
	sleep 1
done
