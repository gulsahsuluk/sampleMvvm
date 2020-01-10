package com.gulsahsuluk.chocochallange.data.exceptions

import java.lang.RuntimeException

class EssentialParamMissingException(missingParams: String,
                                     rawObject: Any):
        RuntimeException("The params: $missingParams are missing in received object: $rawObject")
