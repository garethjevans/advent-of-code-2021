def realData = new File('input.txt').text.split("\n").collect{Integer.parseInt(it)}
def testData = new File('test-data.txt').text.split("\n").collect{Integer.parseInt(it)}

def slidingWindows(input) {
    windows = input.collate(3, 1, false)
    assert windows.size() == input.size() - 2
    return windows.collect{ it.sum() } 
}

def countIncreases(input) {
    delta = input.withIndex().collect { element, index -> index == 0 ? false : input[index] > input[index-1] }
    return delta.sum{ it ? 1 : 0 }
}

assert 7 == countIncreases(testData)
println countIncreases(realData)

assert 5 == countIncreases(slidingWindows(testData))
println countIncreases(slidingWindows(realData))


