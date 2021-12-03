#!/usr/bin/env python
# -*- coding: utf-8 -*-

import pytest
from urbancode import load, count_increases, sliding_window

def test_load_testdata():
	d = load('test-data.txt')
	assert len(d) == 10

def test_load_realdata():
	d = load('input.txt')
	assert len(d) == 2000

def test_count_increases():
    d = load('test-data.txt')
    assert count_increases(d) == 7

def test_sliding_window():
    d = load('test-data.txt')
    assert count_increases(sliding_window(d)) == 5
