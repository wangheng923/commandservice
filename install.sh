#!/bin/sh
yum -y install epel-release 
yum -y install docker python python-pip
pip install --upgrade pip
pip install docker-compose
systemctl enable docker
systemctl start docker
