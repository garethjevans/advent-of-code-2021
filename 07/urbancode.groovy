input = new File('real-data.txt').text.trim().split(',').collect{ Integer.parseInt(it) }

// triangle numbers
def calculateFuel = { n -> (n*(n+1))/2 }

results = (0..input.max()).collect{ pos -> 
    [pos: pos, 
        moves: input.sum{ i -> Math.abs(i-pos) }, 
        fuel: input.sum{ i -> calculateFuel(Math.abs(i-pos)) } ]
}

println "=========================="
println "Part1: " + results.min{ it.moves }.moves
println "=========================="
println "Part2: " + results.min{ it.fuel }.fuel
println "=========================="
