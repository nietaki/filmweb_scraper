#!/bin/bash
awk '{printf "%d,%s\n", NR -1, $0}'
