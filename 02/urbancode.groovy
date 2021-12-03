


class Command {
    String direction
    int distance

    def String toString() {
        return "Command direction=$direction, distance=${distance}"
    }
}

def getInput(file) {
    return new File(file).text.split("\n").collect{ new Command(direction: it.split(" ")[0], distance: Integer.parseInt(it.split(" ")[1]))}
}

def getTotal(c) {
    depth = 0
    horizontial = 0
    
    c.each{ it -> 
        if (it.direction == 'down') {
          depth = depth + it.distance
        } else if (it.direction == 'up') {
          depth = depth - it.distance
        } else {
          horizontial = horizontial + it.distance
        }
    }

    return depth * horizontial
}

def getAimTotal(c) {
    depth = 0
    horizontial = 0
    aim = 0

    c.each{ it -> 
        if (it.direction == 'down') {
          aim = aim + it.distance
        } else if (it.direction == 'up') {
          aim = aim - it.distance
        } else {
          horizontial = horizontial + it.distance
          depth = depth + (it.distance * aim)
        }
    }

    return depth * horizontial
}

commands = getInput('test-data.txt')
assert getTotal(commands) == 150
assert getAimTotal(commands) == 900

println getTotal(commands)
println getAimTotal(commands)

commands = getInput('real-data.txt')

println getTotal(commands)
assert getTotal(commands) == 1936494
println getAimTotal(commands)
