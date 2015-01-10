#!/bin/bash
awk '{printf "%d,%s\n", NR -2, $0}'
