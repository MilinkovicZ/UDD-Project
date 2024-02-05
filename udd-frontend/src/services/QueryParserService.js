const parseFilters = (filters) => {
    let result = '';
  
    filters.forEach((filter, index) => {
      if (filter.id !== 0 && filter.operator) {
        result += `${filter.operator} `;
      }
  
      if (filter.isNot) {
        result += 'NOT ';
      }
  
      result += `${filter.field}:${filter.isPhrase ? '"' + filter.value + '"' : filter.value}`;
  
      if (index < filters.length - 1) {
        result += ' ';
      }
    });
  
    return result;
  };
  
  export default parseFilters;