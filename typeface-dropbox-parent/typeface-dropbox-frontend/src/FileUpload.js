import React, { useRef, useState } from 'react';
import { uploadFile } from './api';
import { Button, Typography, Box } from '@mui/material';

const ACCEPTED_TYPES = '.txt,.json,.jpg,.jpeg,.png';

export default function FileUpload({ onUpload }) {
  const fileInput = useRef();
  const [error, setError] = useState('');
  const [uploading, setUploading] = useState(false);

  const resetFileInput = () => {
    if (fileInput.current) {
      fileInput.current.value = '';
    }
  };

  const handleChange = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setError('');
    setUploading(true);

    try {
      await uploadFile(file);
      onUpload && onUpload();
      resetFileInput();
    } catch (err) {
      setError(err.message);
    } finally {
      setUploading(false);
    }
  };

  return (
    <Box my={2}>
      <Button variant="contained" component="label" disabled={uploading}>
        Upload File
        <input
          type="file"
          accept={ACCEPTED_TYPES}
          hidden
          ref={fileInput}
          onChange={handleChange}
        />
      </Button>
      {error && <Typography color="error">{error}</Typography>}
    </Box>
  );
}
