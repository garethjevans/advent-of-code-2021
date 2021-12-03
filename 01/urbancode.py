#!/usr/bin/env python
# -*- coding: utf-8 -*-

def load(file):
  f = open(file, "r")
  data = []
  for x in f: 
    data[len(data):] = [int(x)]
  return data

def count_increases(input):
  prev = 0
  i = 0
  for x in input:
    if x > prev:
        if prev != 0:
          i = i + 1
          print("found an increase!")

    prev = x
  return i

def sliding_window(input):
    data = []
    for index in range(len(input)-2):
      data[len(data):] = [(input[index] + input[index+1] + input[index+2])]

    return data


print(count_increases(load("input.txt")))
print(count_increases(sliding_window(load("input.txt"))))
