#!/bin/sh

M2_REPO=/Users/shibaevv/Documents/dev/.m2/repo
JBOSS_HOME=/Users/shibaevv/Documents/dev/jboss/jboss-as-7.2.0.Final

rm -f $JBOSS_HOME/standalone/deployments/ats-*.*
cp $M2_REPO/net/apollosoft/ats/ats-web/1.0.0/ats-web-1.0.0.war $JBOSS_HOME/standalone/deployments

rm -rf $JBOSS_HOME/standalone/tmp/vfs
