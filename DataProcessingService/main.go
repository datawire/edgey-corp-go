package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
)

var color string = "green"
var env string = "local"
var datastoreURL string = "http://verylargedatastore:8080"
var port string = "3000"

func index(w http.ResponseWriter, _ *http.Request) {
	fmt.Println("root endpoint entry for DataProcessingService in Go")
	b, err := json.Marshal("root endpoint entry for DataProcessingService in Go")
	if err != nil {
		log.Fatal(err)
		return
	}
	w.Write(b)
}

func getColor(w http.ResponseWriter, _ *http.Request) {
	fmt.Println("color endpoint entry: " + color)
	b, err := json.Marshal(color)
	if err != nil {
		log.Fatal(err)
		return
	}
	w.Write(b)
}

func getEnvironment(w http.ResponseWriter, _ *http.Request) {
	fmt.Println("environment endpoint entry: ", env)
	b, err := json.Marshal(env)
	if err != nil {
		log.Fatal(err)
		return
	}
	w.Write(b)
}

// recordCount (get the number of records via a call to the datastore service)
func getRecordCount(w http.ResponseWriter, _ *http.Request) {
	resp, err := http.Get(datastoreURL + "/recordCount")
	if err != nil {
		log.Fatal(err)
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatal(err)
		return
	}
	fmt.Println("recordCount endpoint entry: ", string(body))
	w.Write(body)
}

// findMerch (find EdgyCorp merchandise matching search params via datastore service)
func getMerch(w http.ResponseWriter, r *http.Request) {
	fmt.Println("findMerch endpoint entry")

	query := r.URL.Query()
	country := query.Get("country")
	season := query.Get("season")
	fmt.Println(country)
	fmt.Println(season)

	// generate searchQuery to send to datastore
	searchString := datastoreURL + "/findMerch?country=" + country + "&season=" + season
	fmt.Println("search string: ", searchString)

	resp, err := http.Get(searchString)
	if err != nil {
		log.Fatal(err)
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatal(err)
		return
	}
	fmt.Println(string(body))
	w.Header().Set("Content-Type", "application/json")
	w.Write(body)
}

func main() {
	fmt.Println("Welcome to the DataProcessingGoService!")

	// Configure app via command line params
	newColor := flag.String("c", color, "a string")
	if *newColor != color {
		color = *newColor
	}
	newEnv := flag.String("env", env, "a string")
	if *newEnv != env {
		env = *newEnv
	}
	newDatastoreURL := flag.String("d", datastoreURL, "a string")
	if *newDatastoreURL != datastoreURL {
		datastoreURL = *newDatastoreURL
	}
	newPort := flag.String("p", port, "an int")
	if *newPort != port {
		port = *newPort
	}

	flag.Parse()

	http.HandleFunc("/", index)
	http.HandleFunc("/color", getColor)
	http.HandleFunc("/environment", getEnvironment)
	http.HandleFunc("/recordCount", getRecordCount)
	http.HandleFunc("/findMerch", getMerch)

	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		log.Fatal("Error Starting the HTTP Server : ", err)
		return
	}

}
