#!/bin/bash

wc `find $1 | grep "\.java$"`
