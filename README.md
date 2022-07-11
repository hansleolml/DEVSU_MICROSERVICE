# Banco Pichincha - DevOps Technical Assessment
## Hosts:

  •	Dev: https://apim-devsu-dev-centralus-001.azure-api.net/dev/DevOps  
  •	Qa: https://apim-devsu-dev-centralus-001.azure-api.net/qa/DevOps  
  •	Prod: https://apim-devsu-dev-centralus-001.azure-api.net/prod/DevOps

## Get token JWT

curl --location --request POST 'https://login.microsoftonline.com/c628c1e7-f421-4cd7-86ba-e82a32c72bd5/oauth2/v2.0/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: fpc=AjFUyeLyUyBHhOiyxU2u-PBkq4uhAQAAAO_7XdoOAAAA; stsservicecookie=estsfd; x-ms-gateway-slice=estsfd' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=506d1da1-8ddb-4b37-bb90-b5533dc75576' \
--data-urlencode 'client_secret=' \
--data-urlencode 'scope=be4b3ba9-27d2-4ffc-9935-14a64bb56696/.default'


## Payload:
{
“message” : “This is a test”,
“to”: “Juan Perez”,
“from”: “Rita Asturia”,
“timeToLifeSec” : 45
}

curl -X POST \
-H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
-H "X-JWT-KWY: ${JWT}" \
-H "Content-Type: application/json" \
-d '{ "message" : "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec" : 45 }' \
https://${HOST}/DevOps

## Response 

{
    "message": "Hello Juan Perez your message will be send"
}
