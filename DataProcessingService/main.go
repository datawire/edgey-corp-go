package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
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
		fmt.Println(err)
		return
	}
	w.Write(b)
}

func getColor(w http.ResponseWriter, _ *http.Request) {
	c := color
	fmt.Println("color endpoint entry: " + c)
	b, err := json.Marshal(c)
	if err != nil {
		fmt.Println(err)
		return
	}
	w.Write(b)
}

func getEnvironment(w http.ResponseWriter, _ *http.Request) {
	e := env
	fmt.Println("environment endpoint entry: ", e)
	b, err := json.Marshal(e)
	if err != nil {
		fmt.Println(err)
		return
	}
	w.Write(b)
}

// recordCount (get the number of records via a call to the datastore service)
func getRecordCount(w http.ResponseWriter, _ *http.Request) {
	d := datastoreURL
	resp, err := http.Get(d + "/recordCount")
	if err != nil {
		fmt.Println(err)
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Println(err)
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
		fmt.Println(err)
		return
	}
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(body))
	w.Header().Set("Content-Type", "application/json")
	w.Write(body)
}

func main() {
	fmt.Println("Welcome to the DataProcessingGoService!")

	// Configure app via command line params
	colorPtr := flag.String("c", color, "a string")
	envPtr := flag.String("env", env, "a string")
	datastorePtr := flag.String("d", datastoreURL, "a string")
	portPtr := flag.String("p", port, "an int")
	flag.Parse()

	// Detects if a command-line flag has been set and updates the variable.
	if *colorPtr != color {
		color = *colorPtr
		fmt.Println("Color set to: ", *colorPtr)
	}
	if *envPtr != env {
		env = *envPtr
		fmt.Println("Environment set to: ", *envPtr)
	}
	if *datastorePtr != datastoreURL {
		datastoreURL = *datastorePtr
		fmt.Println("Datastore URL set to: ", *datastorePtr)
	}
	if *portPtr != port {
		port = *portPtr
		fmt.Println("Port set to: ", *portPtr)
	}

	http.HandleFunc("/", index)
	http.HandleFunc("/color", getColor)
	http.HandleFunc("/environment", getEnvironment)
	http.HandleFunc("/recordCount", getRecordCount)
	http.HandleFunc("/findMerch", getMerch)

	err := http.ListenAndServe(":"+port, nil)
	if err != nil {
		fmt.Println("Error Starting the HTTP Server : ", err)
		return
	}

}
