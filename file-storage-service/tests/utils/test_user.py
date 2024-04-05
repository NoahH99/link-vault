import hashlib
import unittest

from api.utils.user import get_user_id, hash_user_id


class TestGetUserId(unittest.TestCase):
    def test_valid_decoded_token(self):
        decoded_token = {"sub": "user123"}

        self.assertEqual(get_user_id(decoded_token), "user123")

    def test_missing_sub_key(self):
        decoded_token = {"other_key": "value"}

        self.assertIsNone(get_user_id(decoded_token))

    def test_none_token(self):
        decoded_token = None

        self.assertIsNone(get_user_id(decoded_token))


class TestHashUserId(unittest.TestCase):
    def test_valid_user_id(self):
        user_id = "user123"
        expected_hash = hashlib.sha256(user_id.encode()).hexdigest()

        self.assertEqual(hash_user_id(user_id), expected_hash)

    def test_empty_user_id(self):
        user_id = ""
        expected_hash = hashlib.sha256(user_id.encode()).hexdigest()

        self.assertEqual(hash_user_id(user_id), expected_hash)

    def test_long_user_id(self):
        user_id = "user123" * 1000
        expected_hash = hashlib.sha256(user_id.encode()).hexdigest()

        self.assertEqual(hash_user_id(user_id), expected_hash)

    def test_none_user_id(self):
        user_id = None

        self.assertIsNone(hash_user_id(user_id))


if __name__ == '__main__':
    unittest.main()
