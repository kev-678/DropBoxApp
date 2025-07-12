import React, { useEffect, useState } from 'react';
import { downloadFile } from './api';
import { Button, Typography, Box } from '@mui/material';

export default function FileViewer({ filename, onBack }) {
  const [content, setContent] = useState('');
  const [type, setType] = useState('');
  const [error, setError] = useState('');

  useEffect(() => {
    if (!filename) return;
    downloadFile(filename)
      .then(blob => {
        setType(blob.type);
        if (blob.type.startsWith('text/')) {
          blob.text().then(setContent);
        } else if (blob.type.startsWith('image/')) {
          setContent(URL.createObjectURL(blob));
        } else {
          setContent('Preview not supported.');
        }
      })
      .catch(e => setError(e.message));
    // Cleanup image URL
    return () => {
      if (type.startsWith('image/') && content) URL.revokeObjectURL(content);
    };
    // eslint-disable-next-line
  }, [filename]);

  return (
    <Box>
      <Button onClick={onBack} variant="outlined" sx={{mb:2}}>Back</Button>
      <Typography variant="h6">Viewing: {filename}</Typography>
      {error && <Typography color="error">{error}</Typography>}
      {type.startsWith('text/') && <Box component="pre" sx={{whiteSpace:'pre-wrap', mt:2}}>{content}</Box>}
      {type.startsWith('image/') && <Box mt={2}><img src={content} alt={filename} style={{maxWidth:'100%'}} /></Box>}
      {!type && !error && <Typography>Loading...</Typography>}
      {!type.startsWith('text/') && !type.startsWith('image/') && type && <Typography>Preview not supported for this file type.</Typography>}
    </Box>
  );
}
