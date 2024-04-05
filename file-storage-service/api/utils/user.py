import hashlib


def get_user_id(decoded_token: dict) -> str | None:
    if decoded_token is None:
        return None

    return decoded_token.get("sub")


def hash_user_id(user_id: str) -> str | None:
    if user_id is None:
        return None

    return hashlib.sha256(user_id.encode()).hexdigest()
