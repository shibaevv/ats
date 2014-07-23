#!/bin/sh

# linux
service jboss7 stop

# vi ~/.bash_profile
M2_REPO=/home/shibaevv/.m2/repository
JBOSS_HOME=/usr/jboss/jboss-as-7.2.0.Final

rm -f $JBOSS_HOME/standalone/deployments/ats-*.*
cp $M2_REPO/net/apollosoft/ats/ats-web/1.0.0/ats-web-1.0.0.war $JBOSS_HOME/standalone/deployments

service jboss7 start
