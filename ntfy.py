from requests import post

def send(Title, Message, address):
    post(f"https://ntfy.sh/{address}", data=Message,headers={
                        "Title": Title,
                        "Priority": "urgent"
                    })