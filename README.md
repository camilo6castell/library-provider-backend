# PINGUINERA-PROVIDER

## IMPLEMENTED TECHNOLOGIES

- Java
- Spring

## USAGE

### 1. You will first need to clone the Git repository

To do this use the following code

```sh
git clone https://github.com/camilo6castell/pinguinera-provider.git
```

### 2. Open the project

Then you should open the project with your favorite IDE to view or edit the project comfortably. I suggest Intellij,

### 3. Run the program

To run the project you must look in the IDE for the symbol to start the server.

### 4. Use specialized software for requests

To test the project you will have to use some software that can make POST requests to servers, one of the best known is POSTMAN.

### 5. Use test data

(I had to use Thunder to make these requests to the endpoint because my PC is low-resource and the operating system I use does not allow non-free software to be easily installed and with Wine Postman it did not work well. For these reasons, and time, I couldn't do it in postman and had to go for the simplest alternative I found: Thunder.)

There is a file in the ./postman-thunder folder in .json format which contains the Requests made to test the endpoints.

However, I show them below

#### endpoint: http://localhost:8080/CalculateRetailSaleQuote

```sh
{
  "title": "1884",
  "type": 0,
  "basePrice": 100,
  "clientEntryDate": "2006-01-01"
}
```

#### endpoint: http://localhost:8080/CalculateWholesaleQuote

```sh
{
  "bookObjectList": [
    {
      "title": "libro1",
      "type": 0,
      "basePrice": 10000
    },
    {
      "title": "libro2",
      "type": 0,
      "basePrice": 200
    },
    {
      "title": "libro3",
      "type": 0,
      "basePrice": 4000
    },
    {
      "title": "1ibro4",
      "type": 0,
      "basePrice": 4
    },
    {
      "title": "1ibro5",
      "type": 0,
      "basePrice": 750
    }
  ],
  "novelObjectList": [
    {
      "title": "novela1",
      "type": 1,
      "basePrice": 10
    },
    {
      "title": "novela2",
      "type": 1,
      "basePrice": 2000
    },
    {
      "title": "novela3",
      "type": 1,
      "basePrice": 4000
    },
    {
      "title": "novela4",
      "type": 1,
      "basePrice": 200
    },
    {
      "title": "novela5",
      "type": 1,
      "basePrice": 200
    },
    {
      "title": "novela6",
      "type": 1,
      "basePrice": 2
    },
    {
      "title": "novela7",
      "type": 1,
      "basePrice": 2000
    }
    ],
  "clientEntryDate": "2006-01-01"
}
```

#### endpoint: http://localhost:8080/CalculateBudgetSaleQuote

```sh
{
  "textList": [
    {
      "title": "1886",
      "type": 1,
      "basePrice": 10000
    },
    {
      "title": "1885",
      "type": 1,
      "basePrice": 200
    },
    {
      "title": "1884",
      "type": 0,
      "basePrice": 400
    },
    {
      "title": "1883",
      "type": 1,
      "basePrice": 400
    },
    {
      "title": "1886",
      "type": 0,
      "basePrice": 10000
    },
    {
      "title": "1885",
      "type": 0,
      "basePrice": 200
    },
    {
      "title": "1884",
      "type": 0,
      "basePrice": 400
    },
    {
      "title": "1883",
      "type": 0,
      "basePrice": 400
    },
    {
      "title": "1886",
      "type": 1,
      "basePrice": 10000
    },
    {
      "title": "1885",
      "type": 1,
      "basePrice": 200
    },
    {
      "title": "1884",
      "type": 0,
      "basePrice": 400
    },
    {
      "title": "1883",
      "type": 0,
      "basePrice": 400
    }
    ],
    "budget": 4900,
    "clientEntryDate": "2006-01-01"
}
```

### 6. Work points to improve (in progress)

I carried out this work in compliance with the proposed objectives, and although the required functionalities work well, they have a limitation.

The server must be restarted for each test performed. This is because the way I declared the properties of the classes makes them persist and the data from one request affects the others.

I noticed this very late in the project and will correct it as soon as I can.
