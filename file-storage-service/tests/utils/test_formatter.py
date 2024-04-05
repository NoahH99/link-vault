import unittest

from api.utils.formatter import human_readable_size


class TestFormatter(unittest.TestCase):
    def test_bytes(self):
        size_in_bytes = 100
        size_in_bytes_formatted = "100.00 B"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_kilobytes(self):
        size_in_bytes = 2048
        size_in_bytes_formatted = "2.00 KB"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_megabytes(self):
        size_in_bytes = 1024 ** 2 * 3
        size_in_bytes_formatted = "3.00 MB"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_gigabytes(self):
        size_in_bytes = 1024 ** 3 * 4
        size_in_bytes_formatted = "4.00 GB"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_terabytes(self):
        size_in_bytes = 1024 ** 4 * 5
        size_in_bytes_formatted = "5.00 TB"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_negative_size(self):
        size_in_bytes = -100
        size_in_bytes_formatted = "-100.00 B"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_zero_size(self):
        size_in_bytes = 0
        size_in_bytes_formatted = "0.00 B"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_decimal_size(self):
        size_in_bytes = 5000
        size_in_bytes_formatted = "4.88 KB"

        self.assertEqual(human_readable_size(size_in_bytes), size_in_bytes_formatted)

    def test_none_size(self):
        size_in_bytes = None

        self.assertIsNone(human_readable_size(size_in_bytes))


if __name__ == '__main__':
    unittest.main()
