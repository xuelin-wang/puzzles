package main

import (
	"net/http"
	"fmt"
	"log"
	"sync"
)

var mu sync.Mutex
var count int

func main() {
	http.HandleFunc("/", handleRoot)
	http.HandleFunc("/count", handleCount)
	log.Fatal(http.ListenAndServe("localhost:2345", nil))
}

func handleRoot(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "URL.Path = %s", r.URL.Path)
	mu.Lock()
	count++
	mu.Unlock()
}

func handleCount(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "count = %d", count)
}