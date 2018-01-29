package main

import (
	"io/ioutil"
	"fmt"
	"os"
	"strings"
)

func main() {
	counts := make(map[string]int)
	//input := bufio.NewScanner(os.Stdin)
	//for input.Scan() {
	//	counts[input.Text()]++
	//}

	for _, filename := range os.Args[1:] {
		data, err := ioutil.ReadFile(filename)
		if err != nil {
			fmt.Fprint(os.Stderr, "dup3: %v\n", err)
			continue
		}

		for _, line := range strings.Split(string(data), "\n") {
			counts[line]++
		}
	}
	for line, n := range counts {
		if n > 1 {
			fmt.Printf("%d\t%s\n", n, line)
		}
	}
}